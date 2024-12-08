import itertools
import time

INPUT_FILE = "input.txt"


def run_part_1():
    input_array = read_input()
    antinodes = set()

    antennas = get_antennas(input_array)

    for key, value in antennas.items():
        combinations = set(itertools.combinations(value, 2))
        for combination in combinations:
            vector = (combination[1][0] - combination[0][0], combination[1][1] - combination[0][1])
            antinode_1 = (combination[0][0] - vector[0], combination[0][1] - vector[1])
            antinode_2 = (combination[1][0] + vector[0], combination[1][1] + vector[1])
            if is_in_bounds(antinode_1[0], antinode_1[1], input_array):
                antinodes.add(antinode_1)
            if is_in_bounds(antinode_2[0], antinode_2[1], input_array):
                antinodes.add(antinode_2)
    return len(antinodes)


def get_antennas(input_array):
    antennas = {}
    for y in range(len(input_array)):
        for x in range(len(input_array[0])):
            if input_array[y][x] != ".":
                if input_array[y][x] not in antennas:
                    antennas[input_array[y][x]] = {(x, y)}
                else:
                    antennas[input_array[y][x]].add((x, y))
    return antennas


def run_part_2():
    input_array = read_input()
    antinodes = set()
    antennas = get_antennas(input_array)

    for key, value in antennas.items():
        combinations = set(itertools.combinations(value, 2))
        for combination in combinations:
            vector = (combination[1][0] - combination[0][0], combination[1][1] - combination[0][1])
            i = 0
            in_bounds_1 = True
            in_bounds_2 = True
            while in_bounds_1 or in_bounds_2:
                antinode_1 = (combination[0][0] - vector[0] * i, combination[0][1] - vector[1] * i)
                antinode_2 = (combination[1][0] + vector[0] * i, combination[1][1] + vector[1] * i)

                in_bounds_1 = is_in_bounds(antinode_1[0], antinode_1[1], input_array)
                in_bounds_2 = is_in_bounds(antinode_2[0], antinode_2[1], input_array)

                if in_bounds_1:
                    antinodes.add(antinode_1)
                if in_bounds_2:
                    antinodes.add(antinode_2)

                i += 1

    return len(antinodes)


def is_in_bounds(x, y, input_array):
    return 0 <= x < len(input_array[0]) and 0 <= y < len(input_array)


def read_input():
    input_array = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            input_array.append(list(line.strip()))

    return input_array


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
