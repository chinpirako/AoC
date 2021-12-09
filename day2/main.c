#include <stdio.h>
#include <string.h>

long part_one(FILE *file)
{
    long x = 0, depth = 0, value = 0;
    char line[20];
    char direction[8];

    while (fgets(line, sizeof(line), file))
    {
        sscanf(line, "%s %ld", direction, &value);
        if (strcmp(direction, "forward") == 0)
            x += value;
        if (strcmp(direction, "down") == 0)
            depth += value;
        if (strcmp(direction, "up") == 0)
            depth -= value;
    }

    return x * depth;
}

long part_two(FILE *file)
{
    long x = 0, depth = 0, aim = 0, value = 0;
    char line[20];
    char direction[8];

    while (fgets(line, sizeof(line), file))
    {
        sscanf(line, "%s %ld", direction, &value);
        if (strcmp(direction, "forward") == 0)
        {
            depth += aim * value;
            x += value;
        }
        if (strcmp(direction, "down") == 0)
            aim += value;
        if (strcmp(direction, "up") == 0)
            aim -= value;
    }

    return x * depth;
}

void main(int argc, char **argv)
{
    FILE *file;
    file = fopen(argv[1], "r");
    long result = part_one(file);
    printf("Part one: %ld\n", result);

    rewind(file);

    result = part_two(file);
    printf("Part two: %ld", result);
}