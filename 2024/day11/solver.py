import time
from collections import Counter

INPUT_FILE = "input.txt"


def get_number_of_digits(rock):
    return len(str(rock))


def blink_with_counter(input_data_counter):
    counter = Counter()

    for rock, count in input_data_counter.items():
        if rock == 0:
            counter[1] += count
        elif get_number_of_digits(rock) % 2 == 0:
            rock_1 = int(str(rock)[:len(str(rock)) // 2])
            rock_2 = int(str(rock)[len(str(rock)) // 2:])
            counter[rock_1] += count
            counter[rock_2] += count
        else:
            counter[rock * 2024] += count

    return counter

def run_part_1():
    input_data = read_input()
    number_of_blinks = 25

    input_data_counter = Counter(input_data)

    for i in range(1, number_of_blinks + 1):
        input_data_counter = blink_with_counter(input_data_counter)

    return input_data_counter.total()


def run_part_2():
    input_data = read_input()
    number_of_blinks = 75

    input_data_counter = Counter(input_data)

    for i in range(1, number_of_blinks + 1):
        input_data_counter = blink_with_counter(input_data_counter)

    return input_data_counter.total()


def read_input():
    with open(INPUT_FILE, "r") as file:
        input_data = [int(l) for l in file.readline().strip().split(" ")]
    return input_data


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
