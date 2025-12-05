# Advent of Code 2025 - Day 4: https://adventofcode.com/2025/day/4
import time

INPUT_FILE = "input.txt"


def get_toilet_paper_positions_and_adjacents(toilet_papers):
    pos_and_adjs = []
    neighbors = [
        (dx, dy) for dx in (-1, 0, 1)
        for dy in (-1, 0, 1)
        if not (dx == 0 and dy == 0)
    ]

    for row in range(len(toilet_papers)):
        for line in range(len(toilet_papers[0])):
            if toilet_papers[row][line] == '.':
                continue
            adjacent_positions = []
            for dx, dy in neighbors:
                new_row = row + dx
                new_line = line + dy
                if 0 <= new_row < len(toilet_papers) and 0 <= new_line < len(toilet_papers[0]) and \
                        toilet_papers[new_row][
                            new_line] == '@':
                    adjacent_positions.append((new_row, new_line))
            to_add = ((row, line), adjacent_positions)
            pos_and_adjs.append(to_add)
    return pos_and_adjs


def run_part_1():
    puzzle_input = read_input()
    pos_and_adjs = get_toilet_paper_positions_and_adjacents(puzzle_input)
    total = len([1 for pos, adjs in pos_and_adjs if len(adjs) < 4])
    return total


def run_part_2():
    puzzle_input = read_input()

    pos_and_adjs = get_toilet_paper_positions_and_adjacents(puzzle_input)
    valid_positions = [pos for pos, adjs in pos_and_adjs if len(adjs) < 4]
    total = len(valid_positions)
    paper_to_remove = set(valid_positions)
    number_of_removable_toilet_papers = len(paper_to_remove)

    while number_of_removable_toilet_papers > 0:
        for pos in paper_to_remove:
            puzzle_input[pos[0]][pos[1]] = '.'
        pos_and_adjs = get_toilet_paper_positions_and_adjacents(puzzle_input)
        valid_positions = [pos for pos, adjs in pos_and_adjs if len(adjs) < 4]
        paper_to_remove = set(valid_positions)
        number_of_removable_toilet_papers = len(paper_to_remove)
        total += number_of_removable_toilet_papers

    return total


def read_input():
    toilet_papers = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            toilet_papers.append(list(line.strip()))
    return toilet_papers


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
