# Advent of Code 2025 - Day 3: https://adventofcode.com/2025/day/3
import time

INPUT_FILE = "example.txt"


def get_max_number_with_two_digits(number_list):
    max_prefix = number_list[0]
    max_number = -1
    for j in range(1, len(number_list)):
        candidate = max_prefix * 10 + number_list[j]
        if candidate > max_number:
            max_number = candidate
        if number_list[j] > max_prefix:
            max_prefix = number_list[j]
    return max_number


def get_max_number_with_twelve_digits(number_list):
    current = []
    start_index = 0
    for remaining in range(12, 0, -1):
        end = len(number_list) - remaining
        max_index = start_index
        max_digit = number_list[start_index]
        for i in range(start_index + 1, end + 1):
            if number_list[i] > max_digit:
                max_digit = number_list[i]
                max_index = i
        current.append(max_digit)
        start_index = max_index + 1
    return int(''.join(str(d) for d in current))


def run_part_1():
    puzzle_input = read_input()
    total = 0
    for row in puzzle_input:
        total += get_max_number_with_two_digits(row)
    return total


def run_part_2():
    puzzle_input = read_input()
    total = 0
    for row in puzzle_input:
        total += get_max_number_with_twelve_digits(row)
    return total


def read_input():
    list_of_batteries = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            list_of_batteries.append(
                [int(i) for i in list(line.strip())]
            )
    return list_of_batteries


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
