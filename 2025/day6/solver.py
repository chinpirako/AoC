# Advent of Code 2025 - Day 6: https://adventofcode.com/2025/day/6
import time

INPUT_FILE = "input.txt"


def run_part_1():
    parsed_input = read_input()
    total = 0
    transposed_input = list(zip(*parsed_input))
    for row in transposed_input:
        operator = row[-1]
        if operator == "+":
            total += sum(int(num) for num in row[:-1])
        elif operator == "*":
            product = 1
            for num in row[:-1]:
                product *= int(num)
            total += product
    return total


def get_numbers(numbers):
    longest_number = max(len(num) for num in numbers)
    final_numbers = []
    for i in range(longest_number):
        current_digits = []
        for num in numbers:
            if len(num) > i:
                current_digits.append(num[-(i + 1)])
        final_number = "".join(current_digits).strip()
        if final_number:
            final_numbers.append(final_number)
    return final_numbers


def run_part_2():
    parsed_input = read_input()
    total = 0
    transposed_input = list(zip(*parsed_input))
    for row in transposed_input:
        operator = row[-1]
        numbers = get_numbers(row[:-1])
        if operator == "+":
            total += sum(int(num) for num in numbers)
        elif operator == "*":
            product = 1
            for num in numbers:
                product *= int(num)
            total += product
    return total


def read_input():
    parsed_input = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        operators_line = lines.pop()
        operators_indexes = [i for i, char in enumerate(operators_line) if char in "+*"]

        for line in lines:
            numbers_line = []
            for i in range(len(operators_indexes)):
                start_idx = operators_indexes[i]
                end_idx = operators_indexes[i + 1] - 1 if i + 1 < len(operators_indexes) else len(line)
                numbers_line.append(line[start_idx:end_idx])
            parsed_input.append(numbers_line)
        parsed_input.append([x for x in list(operators_line.strip()) if x in "+*"])
    return parsed_input


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
