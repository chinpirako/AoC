#include <stdio.h>
#include <string.h>

long part_one(FILE *file)
{
    long x1, x2, value, result = 0;
    int line_number = 0;
    char line[10];
    while (fgets(line, sizeof(line), file))
    {
        sscanf(line, "%ld", &value);
        if (line_number == 0)
            x1 = value;
        else
        {
            x1 = x2;
            x2 = value;
        }
        if (x1 < x2)
            result++;
        line_number++;
    }

    return result;
}

long part_two(FILE *file, int lines)
{
    long x1, x2, x3, value, result = 0;
    int line_number = 0;
    char line[10];
    int values[lines];
    int i = 2;
    while (fgets(line, sizeof(line), file))
    {
        sscanf(line, "%ld", &value);
        values[line_number] = value;
        line_number++;
    }
    for (i; i < lines - 1; i++)
    {
        result += values[i + 1] > values[i - 2] ? 1 : 0;
    }
    return result;
}

void main(int argc, char **argv)
{
    FILE *file;
    file = fopen(argv[1], "r");
    long result = part_one(file);
    printf("Part one: %ld\n", result);

    rewind(file);

    result = part_two(file, atoi(argv[2]));
    printf("Part two: %ld", result);
    fclose(file);
}