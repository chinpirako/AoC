import time

INPUT_FILE = "input.txt"


def recurse(current_number, numbers, available_operators, expected_result, current_formula=""):
    result = 0
    for operator in available_operators:
        if len(numbers) == 0:
            if int(current_number) == expected_result:
                return expected_result
            else:
                return 0
        next_number = numbers[0]
        if operator == "+":
            result += max(0,
                          recurse(int(current_number) + next_number, numbers[1:], available_operators, expected_result,
                                  current_formula + f" + {next_number}")) if result == 0 else 0
        elif operator == "*":
            result += max(0,
                          recurse(int(current_number) * next_number, numbers[1:], available_operators, expected_result,
                                  current_formula + f" * {next_number}")) if result == 0 else 0
        elif operator == "||":
            result += max(0, recurse(int(str(current_number) + str(next_number)), numbers[1:], available_operators,
                                     expected_result, current_formula + f" || {next_number}")) if result == 0 else 0
    return result


def run_part_1_recurse():
    input_data = read_input()
    valid_values = []
    available_operators = {"+", "*"}
    for expected_result, numbers in input_data:
        first_number = numbers[0]
        valid_values.append(
            recurse(first_number, numbers[1:], available_operators, expected_result, f"{first_number}"))

    result = sum(set(valid_values))
    return result, valid_values


def run_part_2():
    input_data = read_input()
    available_operators = {"+", "*", "||"}
    valid_values = []

    for expected_result, numbers in input_data:
        first_number = numbers[0]
        valid_values.append(
            recurse(first_number, numbers[1:], available_operators, expected_result, f"{first_number}"))

    result = sum(valid_values)
    return result


def read_input():
    input_data = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            split_line = line.split(": ")
            expected_result = int(split_line[0].strip())
            numbers = [int(n) for n in split_line[1].split(" ")]
            equation = (expected_result, numbers)
            input_data.append(equation)
    return input_data


if __name__ == "__main__":
    start_time = time.time()
    result_part_1, _ = run_part_1_recurse()
    print(result_part_1)
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
