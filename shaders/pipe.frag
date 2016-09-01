#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
    vec2 tc;
    vec3 position;
} fs_in;

uniform vec2 bird;
uniform sampler2D tex;
uniform int top;

void main()
{
    vec2 new_tc = fs_in.tc;

    if (top == 1)
        new_tc.y = 1.0 - fs_in.tc.y;

    color = texture(tex, new_tc);
    if (color.w < 1.0)
            discard;

    color *= 3.0 / (length(bird - fs_in.position.xy) + 2.5) + 0.4;
    color.w = 1;
}