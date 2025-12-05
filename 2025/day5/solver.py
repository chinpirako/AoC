# Advent of Code 2025 - Day 5: https://adventofcode.com/2025/day/5
import time

INPUT_FILE = "input.txt"

def merge_ranges(ranges):
    ranges.sort(key=lambda x: x[0])
    merged = [ranges[0]]
    for current in ranges[1:]:
        last_merged = merged[-1]
        if current[0] <= last_merged[1]:
            merged[-1] = (last_merged[0], max(last_merged[1], current[1]))
        else:
            merged.append(current)
    return merged


def run_part_1():
    ranges, ingredients_ids = read_input()
    total = 0
    for ingredient_id in ingredients_ids:
        for start, end in ranges:
            if start <= ingredient_id <= end:
                total += 1
                break
    return total


def run_part_2():
    ranges, ingredients_ids = read_input()
    merged_ranges = merge_ranges(ranges)
    total = sum(end - start + 1 for start, end in merged_ranges)
    return total


def read_input():
    ranges = []
    ingredients_ids = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            if line.strip() == "":
                continue
            if "-" in line:
                start, end = line.strip().split("-")
                ranges.append((int(start), int(end)))
            else:
                ingredients_ids.append(int(line.strip()))
    return ranges, ingredients_ids


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
