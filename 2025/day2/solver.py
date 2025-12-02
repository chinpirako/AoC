# Advent of Code 2025 - Day 2: https://adventofcode.com/2025/day/2

import re
import time

INPUT_FILE = "input.txt"


def run_part_1():
    puzzle_input = read_input()
    pattern = re.compile(r'^(\d+)\1$')
    total = 0
    for start, end in puzzle_input:
        for x in range(start, end + 1):
            if pattern.match(str(x)):
                total += x
    return total


def run_part_2():
    puzzle_input = read_input()
    pattern = re.compile(r'^(\d+)\1+$')
    total = 0
    for start, end in puzzle_input:
        for x in range(start, end + 1):
            if pattern.match(str(x)):
                total += x
    return total


def read_input():
    list_of_ranges = []
    with open(INPUT_FILE, "r") as file:
        line = file.readlines()[0].strip()
        ranges = line.split(",")
        for r in ranges:
            start = int(r.split("-")[0])
            end = int(r.split("-")[1])
            list_of_ranges.append((start, end))
    return list_of_ranges


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
