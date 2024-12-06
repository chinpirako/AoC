import re
import time

INPUT_FILE = "input.txt"


def find_and_add_mul(input_data):
    result = 0
    matches = re.finditer(r'mul\((\d+),(\d+)\)', input_data, re.MULTILINE)
    for _, match in enumerate(matches, start=1):
        result += int(match.group(1)) * int(match.group(2))
    return result


def run_part_1():
    input_data = read_input()
    result = find_and_add_mul(input_data)

    return result


def run_part_2():
    input_data = read_input()
    sub_result = re.sub(r"don't\(\).*?do\(\)", "", f"{input_data}do()", 0, re.MULTILINE)
    result = find_and_add_mul(sub_result)

    return result


def read_input():
    with open(INPUT_FILE, "r") as file:
        input_data = ''.join([l.strip() for l in file.readlines()])

    return input_data


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
