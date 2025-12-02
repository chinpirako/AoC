# Advent of Code 2025 - Day 1: https://adventofcode.com/2025/day/1

import re
import time

INPUT_FILE = "input.txt"


def run_part_1():
    start_position = 50
    rotations = read_input()
    number_of_zeros = 0
    for rotation in rotations:
        start_position = (start_position + rotation) % 100
        number_of_zeros += start_position == 0
    return number_of_zeros


def run_part_2():
    start_position = 50
    rotations = read_input()
    number_of_zeros = 0
    for rotation in rotations:
        step = 1 if rotation > 0 else -1
        number_of_zeros += sum(
            1 for i in range(1, abs(rotation) + 1)
            if (start_position + i * step) % 100 == 0
        )
        start_position = (start_position + rotation) % 100

    return number_of_zeros


def read_input():
    rotations = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        regex = r'(R|L)(\d+)'
        for line in lines:
            matches = re.findall(regex, line)
            for match in matches:
                direction = match[0]
                distance = int(match[1]) if direction == 'R' else -int(match[1])
                rotations.append(distance)
    return rotations


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
