#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
    vec2 tc;
    vec3 position;
} fs_in;

uniform float bird;
uniform sampler2D tex;

void main()
{
    color = texture(tex, fs_in.tc);
    color *= min(2.0 / (length(vec2(0, bird) - fs_in.position.xy) / 0.2) + 0.8, 1.2);
}