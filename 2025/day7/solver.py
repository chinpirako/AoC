# Advent of Code 2025 - Day 7: https://adventofcode.com/2025/day/7
import time

INPUT_FILE = "input.txt"


def get_start_position(parsed_input):
    start_position = (0, 0)
    for i, line in enumerate(parsed_input):
        for j, char in enumerate(line):
            if char == "S":
                start_position = (i, j)
                break
    return start_position


def get_splitter_positions(parsed_input):
    splitter_positions = []
    for i, line in enumerate(parsed_input):
        for j, char in enumerate(line):
            if char == "^":
                splitter_positions.append((i, j))
    return splitter_positions


def get_next_beam_positions(pos, splitters):
    next_positions = []
    number_of_splits = 0
    candidate_pos = (pos[0] + 1, pos[1])
    if candidate_pos in splitters:
        number_of_splits += 1
        next_positions.append((candidate_pos[0], candidate_pos[1] - 1))
        next_positions.append((candidate_pos[0], candidate_pos[1] + 1))
    else:
        next_positions.append(candidate_pos)
    return next_positions, number_of_splits


def compute_beams_positions(current_beam_positions, parsed_input, path_tree, splitter_positions):
    total = 0
    while not all(
            pos[0] == len(parsed_input) - 1 for pos in current_beam_positions
    ):
        next_beam_positions = []
        for beam_position in current_beam_positions:
            next_positions, splits_nb = get_next_beam_positions(beam_position, splitter_positions)
            next_beam_positions.extend(next_positions)
            total += splits_nb
            path_tree[beam_position] = next_positions
        current_beam_positions = list(set(next_beam_positions))
    return total


def count_paths(position, parsed_input, path_tree, memo=None):
    if memo is None:
        memo = {}
    if position in memo:
        return memo[position]
    if position[0] == len(parsed_input) - 1:
        memo[position] = 1
        return 1
    if position not in path_tree:
        memo[position] = 0
        return 0
    total_paths = 0
    for next_position in path_tree[position]:
        total_paths += count_paths(next_position, parsed_input, path_tree, memo)
    memo[position] = total_paths
    return total_paths


def run_part_1():
    parsed_input = read_input()
    total = 0
    start_position = get_start_position(parsed_input)
    splitter_positions = get_splitter_positions(parsed_input)
    current_beam_positions = [(start_position[0], start_position[1])]
    total += compute_beams_positions(current_beam_positions, parsed_input, {}, splitter_positions)
    return total


def run_part_2():
    parsed_input = read_input()
    total = 0
    path_tree = {}
    start_position = get_start_position(parsed_input)
    splitter_positions = get_splitter_positions(parsed_input)
    current_beam_positions = [(start_position[0], start_position[1])]
    compute_beams_positions(current_beam_positions, parsed_input, path_tree, splitter_positions)

    for start in path_tree.get(start_position, []):
        total += count_paths(start, parsed_input, path_tree)

    return total


def read_input():
    parsed_input = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            line = line.strip()
            parsed_input.append(line)
    return parsed_input


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
