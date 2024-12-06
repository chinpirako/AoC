import time

INPUT_FILE = "input.txt"


def check_if_safe(data):
    pair_differences = []
    all_same_sign = True
    all_in_range = True
    for i in range(len(data) - 1):
        pair_differences.append(int(data[i]) - int(data[i + 1]))
    for i in range(len(pair_differences) - 1):
        if pair_differences[i] * pair_differences[i + 1] < 0:
            all_same_sign = False
            break
    for i in range(len(pair_differences)):
        if abs(pair_differences[i]) > 3 or abs(pair_differences[i]) < 1:
            all_in_range = False
            break
    return 1 if all_same_sign and all_in_range else 0


def run_part_1():
    input_data = read_input()
    result = 0

    for data in input_data:
        result += check_if_safe(data)

    return result


def run_part_2():
    input_data = read_input()
    result = 0

    for data in input_data:
        is_safe = check_if_safe(data)
        if is_safe:
            result += 1
            continue

        for i in range(len(data)):
            new_data = data.copy()
            new_data.pop(i)
            is_safe = check_if_safe(new_data)
            if is_safe:
                result += 1
                break

    return result


def read_input():
    input_data = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            input_data.append(line.strip().split(" "))
    return input_data


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
