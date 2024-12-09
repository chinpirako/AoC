import time
from itertools import groupby

INPUT_FILE = "input.txt"


def run_part_1():
    input_data = read_input()
    result = 0

    dots = sorted(list(input_data['.']))
    number_keys = sorted([l for l in input_data.keys() if l != '.'], reverse=True)
    for key in number_keys:
        length = len(input_data[key])
        dots_sublist = [d for d in dots[:length] if d < max(input_data[key])]
        dots = dots[length:]
        input_data[key] = set(dots_sublist) | set(list(input_data[key])[:length - len(dots_sublist)])
    result += sum(key * index for key in number_keys for index in input_data[key])
    return result


def run_part_2():
    input_data = read_input()
    result = 0

    dots = input_data['.']
    number_keys = sorted([l for l in input_data.keys() if l != '.'], reverse=True)

    contiguous_dots = get_contiguous_ranges(dots)
    for key in number_keys:
        indexes = input_data[key]
        length = len(indexes)
        sequence_found = [l for l in contiguous_dots if len(l) >= length]
        if sequence_found:
            for i in range(length):
                current_index = max(indexes)
                min_dot = min(sequence_found[0])
                if min_dot >= current_index:
                    break
                indexes.remove(current_index)
                dots.remove(min_dot)
                indexes.add(min_dot)
                sequence_found[0].remove(min_dot)
    result += sum(key * index for key in number_keys for index in input_data[key])
    return result


def get_contiguous_ranges(dots_array):
    group = (n - i for i, n in enumerate(dots_array))
    return [g for _, (*g,) in groupby(dots_array, lambda _: next(group))]


def read_input():
    input_data = dict()
    with open(INPUT_FILE, "r") as file:
        i = 0
        j = 0
        was_digit = False
        for digit in list(file.readline()):
            if not was_digit:
                input_data[int(j)] = set(range(i, i + int(digit)))
                j += 1
                was_digit = True
            else:
                if '.' not in input_data:
                    input_data['.'] = set(range(i, i + int(digit)))
                else:
                    input_data['.'] = input_data['.'] | set(range(i, i + int(digit)))
                was_digit = False
            i += int(digit)
    return input_data


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
