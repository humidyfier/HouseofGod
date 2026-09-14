package com.example.houseofgod.core.designsystem

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import kotlin.math.cos
import kotlin.math.sin

/**
 * AGSL (Android Graphics Shading Language) translation of the React Bits <Grainient /> shader.
 * Configured specifically for mobile phone screens: adapts coordinate scaling and rotation
 * so the full multi-layer gradient waves, warping, and film grain shrink to fit phone aspect ratios.
 */
private const val GRAINIENT_AGSL = """
uniform float2 iResolution;
uniform float iTime;
uniform float uTimeSpeed;
uniform float uColorBalance;
uniform float uWarpStrength;
uniform float uWarpFrequency;
uniform float uWarpSpeed;
uniform float uWarpAmplitude;
uniform float uBlendAngle;
uniform float uBlendSoftness;
uniform float uRotationAmount;
uniform float uNoiseScale;
uniform float uGrainAmount;
uniform float uGrainScale;
uniform float uGrainAnimated;
uniform float uContrast;
uniform float uGamma;
uniform float uSaturation;
uniform float2 uCenterOffset;
uniform float uZoom;
uniform float3 uColor1;
uniform float3 uColor2;
uniform float3 uColor3;
uniform float uLightMode;

float toRad(float deg) {
    return deg * 0.017453292519943295;
}

float2 rot(float2 p, float a) {
    float s = sin(a);
    float c = cos(a);
    return float2(p.x * c + p.y * s, -p.x * s + p.y * c);
}

float2 hash(float2 p) {
    p = float2(dot(p, float2(2127.1, 81.17)), dot(p, float2(1269.5, 283.37)));
    return fract(float2(sin(p.x) * 43758.5453, sin(p.y) * 43758.5453));
}

float noise(float2 p) {
    float2 i = floor(p);
    float2 f = fract(p);
    float2 u = f * f * (3.0 - 2.0 * f);
    float n = mix(
        mix(dot(-1.0 + 2.0 * hash(i + float2(0.0, 0.0)), f - float2(0.0, 0.0)),
            dot(-1.0 + 2.0 * hash(i + float2(1.0, 0.0)), f - float2(1.0, 0.0)), u.x),
        mix(dot(-1.0 + 2.0 * hash(i + float2(0.0, 1.0)), f - float2(0.0, 1.0)),
            dot(-1.0 + 2.0 * hash(i + float2(1.0, 1.0)), f - float2(1.0, 1.0)), u.x),
        u.y
    );
    return 0.5 + 0.5 * n;
}

half4 main(float2 fragCoord) {
    float t = iTime * uTimeSpeed;
    float2 uv = fragCoord / iResolution.xy;
    float ratio = iResolution.x / iResolution.y;

    // Adapt to phone portrait screen:
    // On desktop, ratio is 16:9 (~1.77) where height is smaller than width.
    // On mobile phone portrait mode (ratio < 1.0), height is more than double width.
    // We scale the coordinates so the entire dynamic wavy gradient blend
    // fits comfortably across the phone viewport instead of being cropped.
    float2 tuv = (uv - 0.5 + uCenterOffset) / max(uZoom, 0.001);
    if (ratio < 1.0) {
        tuv.y *= (ratio * 1.5);
    }

    float degree = noise(float2(t * 0.1, tuv.x * tuv.y) * uNoiseScale);
    tuv.y *= 1.0 / ratio;
    tuv = rot(tuv, toRad((degree - 0.5) * uRotationAmount + 180.0));
    tuv.y *= ratio;

    float frequency = uWarpFrequency;
    float ws = max(uWarpStrength, 0.001);
    float amplitude = uWarpAmplitude / ws;
    float warpTime = t * uWarpSpeed;
    tuv.x += sin(tuv.y * frequency + warpTime) / amplitude;
    tuv.y += sin(tuv.x * (frequency * 1.5) + warpTime) / (amplitude * 0.5);

    float3 colLav = uColor1;
    float3 colOrg = uColor2;
    float3 colDark = uColor3;
    float b = uColorBalance;
    float s = max(uBlendSoftness, 0.0);

    float blendX = rot(tuv, toRad(uBlendAngle)).x;
    float edge0 = -0.3 - b - s;
    float edge1 = 0.2 - b + s;
    float v0 = 0.5 - b + s;
    float v1 = -0.3 - b - s;

    float3 layer1 = mix(colDark, colOrg, smoothstep(edge0, edge1, blendX));
    float3 layer2 = mix(colOrg, colLav, smoothstep(edge0, edge1, blendX));
    float3 col = mix(layer1, layer2, smoothstep(v0, v1, tuv.y));

    float2 grainUv = uv * max(uGrainScale, 0.001);
    if (uGrainAnimated > 0.5) {
        grainUv += float2(iTime * 0.05, iTime * 0.05);
    }
    float grain = fract(sin(dot(grainUv, float2(12.9898, 78.233))) * 43758.5453);
    col += (grain - 0.5) * uGrainAmount;

    col = (col - 0.5) * uContrast + 0.5;
    float luma = dot(col, float3(0.2126, 0.7152, 0.0722));
    col = mix(float3(luma), col, uSaturation);
    col = pow(max(col, 0.0), float3(1.0 / max(uGamma, 0.001)));
    col = clamp(col, 0.0, 1.0);

    if (uLightMode > 0.5) {
        float energy = max(max(col.r, col.g), col.b);
        float3 hue = col / max(energy, 0.001);
        float chroma = length(col - float3(dot(col, float3(0.333333))));
        float coverage = clamp(0.12 + chroma * 1.15 + energy * 0.18, 0.0, 0.88);
        col = mix(float3(1.0), clamp(hue * 0.58 + col * 0.18, 0.0, 1.0), coverage);
    }

    return half4(col, 1.0);
}
"""

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class GrainientRuntimeShaderHolder {
    val shader = android.graphics.RuntimeShader(GRAINIENT_AGSL)
    val brush = ShaderBrush(shader)

    fun update(
        width: Float,
        height: Float,
        time: Float,
        timeSpeed: Float,
        colorBalance: Float,
        warpStrength: Float,
        warpFrequency: Float,
        warpSpeed: Float,
        warpAmplitude: Float,
        blendAngle: Float,
        blendSoftness: Float,
        rotationAmount: Float,
        noiseScale: Float,
        grainAmount: Float,
        grainScale: Float,
        grainAnimated: Boolean,
        contrast: Float,
        gamma: Float,
        saturation: Float,
        centerX: Float,
        centerY: Float,
        zoom: Float,
        color1: Color,
        color2: Color,
        color3: Color
    ) {
        shader.setFloatUniform("iResolution", width, height)
        shader.setFloatUniform("iTime", time)
        shader.setFloatUniform("uTimeSpeed", timeSpeed)
        shader.setFloatUniform("uColorBalance", colorBalance)
        shader.setFloatUniform("uWarpStrength", warpStrength)
        shader.setFloatUniform("uWarpFrequency", warpFrequency)
        shader.setFloatUniform("uWarpSpeed", warpSpeed)
        shader.setFloatUniform("uWarpAmplitude", warpAmplitude)
        shader.setFloatUniform("uBlendAngle", blendAngle)
        shader.setFloatUniform("uBlendSoftness", blendSoftness)
        shader.setFloatUniform("uRotationAmount", rotationAmount)
        shader.setFloatUniform("uNoiseScale", noiseScale)
        shader.setFloatUniform("uGrainAmount", grainAmount)
        shader.setFloatUniform("uGrainScale", grainScale)
        shader.setFloatUniform("uGrainAnimated", if (grainAnimated) 1.0f else 0.0f)
        shader.setFloatUniform("uContrast", contrast)
        shader.setFloatUniform("uGamma", gamma)
        shader.setFloatUniform("uSaturation", saturation)
        shader.setFloatUniform("uCenterOffset", centerX, centerY)
        shader.setFloatUniform("uZoom", zoom)
        shader.setFloatUniform("uColor1", color1.red, color1.green, color1.blue)
        shader.setFloatUniform("uColor2", color2.red, color2.green, color2.blue)
        shader.setFloatUniform("uColor3", color3.red, color3.green, color3.blue)
        shader.setFloatUniform("uLightMode", 0.0f)
    }
}

/**
 * Grainient motion graphics background component based on React Bits Grainient.
 *
 * Implements hardware-accelerated AGSL on Android 13+ (API 33+) and a multi-layer
 * harmonic gradient wave fallback for Android 8-12 and JVM unit tests.
 */
@Composable
fun GrainientBackground(
    modifier: Modifier = Modifier,
    color1: Color = Color(0xFFA362A1),
    color2: Color = Color(0xFF9885E6),
    color3: Color = Color(0xFFC19DE3),
    timeSpeed: Float = 1.0f,
    colorBalance: Float = 0.1f,
    warpStrength: Float = 1.0f,
    warpFrequency: Float = 5.0f,
    warpSpeed: Float = 2.0f,
    warpAmplitude: Float = 50.0f,
    blendAngle: Float = 0.0f,
    blendSoftness: Float = 0.05f,
    rotationAmount: Float = 500.0f,
    noiseScale: Float = 2.0f,
    grainAmount: Float = 0.1f,
    grainScale: Float = 2.0f,
    grainAnimated: Boolean = false,
    contrast: Float = 1.5f,
    gamma: Float = 1.0f,
    saturation: Float = 1.0f,
    centerX: Float = -0.09f,
    centerY: Float = 0.0f,
    zoom: Float = 0.9f,
    darkScrimAlpha: Float = 0.35f,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val isShaderSupported = remember {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                try {
                    Class.forName("android.graphics.RuntimeShader") != null
                } catch (_: Throwable) {
                    false
                }
    }

    // Continuous vsync time accumulator for smooth animation
    val timeState = produceState(initialValue = 0f) {
        val startTime = System.nanoTime()
        while (true) {
            withFrameNanos { now ->
                value = (now - startTime) / 1_000_000_000f
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (isShaderSupported) {
            val shaderHolder = remember { GrainientRuntimeShaderHolder() }

            Canvas(modifier = Modifier.fillMaxSize()) {
                shaderHolder.update(
                    width = size.width,
                    height = size.height,
                    time = timeState.value,
                    timeSpeed = timeSpeed,
                    colorBalance = colorBalance,
                    warpStrength = warpStrength,
                    warpFrequency = warpFrequency,
                    warpSpeed = warpSpeed,
                    warpAmplitude = warpAmplitude,
                    blendAngle = blendAngle,
                    blendSoftness = blendSoftness,
                    rotationAmount = rotationAmount,
                    noiseScale = noiseScale,
                    grainAmount = grainAmount,
                    grainScale = grainScale,
                    grainAnimated = grainAnimated,
                    contrast = contrast,
                    gamma = gamma,
                    saturation = saturation,
                    centerX = centerX,
                    centerY = centerY,
                    zoom = zoom,
                    color1 = color1,
                    color2 = color2,
                    color3 = color3
                )
                drawRect(brush = shaderHolder.brush)

                // Soft dark devotional scrim to preserve white typography legibility
                if (darkScrimAlpha > 0f) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0A0C10).copy(alpha = darkScrimAlpha * 0.7f),
                                Color(0xFF0A0C10).copy(alpha = darkScrimAlpha * 0.3f),
                                Color(0xFF0A0C10).copy(alpha = darkScrimAlpha)
                            )
                        )
                    )
                }
            }
        } else {
            // Pure Compose Canvas fallback for API < 33 and JVM test runners
            val infiniteTransition = rememberInfiniteTransition(label = "GrainientFallbackMotion")
            val animProgress by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 10000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "GrainientFallbackProgress"
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val t = animProgress * 6.2831853f * timeSpeed
                val maxDim = maxOf(size.width, size.height)

                // Base deep tone
                drawRect(color = color3)

                // Animated secondary accent wave
                val offset2 = Offset(
                    x = size.width * (0.5f + centerX + 0.3f * sin(t * 0.7f)),
                    y = size.height * (0.45f + centerY + 0.25f * cos(t * 0.5f))
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(color2.copy(alpha = 0.90f), color2.copy(alpha = 0f)),
                        center = offset2,
                        radius = maxDim * 0.85f
                    ),
                    center = offset2,
                    radius = maxDim * 0.85f
                )

                // Animated primary light wave
                val offset1 = Offset(
                    x = size.width * (0.5f - centerX + 0.35f * cos(t * 0.6f)),
                    y = size.height * (0.55f - centerY + 0.3f * sin(t * 0.8f))
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(color1.copy(alpha = 0.85f), color1.copy(alpha = 0f)),
                        center = offset1,
                        radius = maxDim * 0.9f
                    ),
                    center = offset1,
                    radius = maxDim * 0.9f
                )

                // Dark devotional overlay
                if (darkScrimAlpha > 0f) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0A0C10).copy(alpha = darkScrimAlpha * 0.7f),
                                Color(0xFF0A0C10).copy(alpha = darkScrimAlpha * 0.3f),
                                Color(0xFF0A0C10).copy(alpha = darkScrimAlpha)
                            )
                        )
                    )
                }
            }
        }

        content()
    }
}
