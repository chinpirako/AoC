import time

INPUT_FILE = "input.txt"


def run_part_1():
    first_array, second_array = read_input()

    first_array.sort()
    second_array.sort()

    result = 0

    # Add 1st element of first array to the 1st element of the second array, and so on
    for i in range(len(first_array)):
        result += abs(int(first_array[i]) - int(second_array[i]))

    return result


def run_part_2():
    first_array, second_array = read_input()
    result = 0
    for i in range(len(first_array)):
        element_to_check = first_array[i]
        count = second_array.count(element_to_check)
        result += count * int(element_to_check)

    return result


def read_input():
    first_array = []
    second_array = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            # Split the line into two arrays
            first_array.append(line.split("   ")[0])
            second_array.append(line.split("   ")[1].strip())

    return first_array, second_array


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
