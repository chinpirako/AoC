import time

INPUT_FILE = "input.txt"

DIRECTIONS = ["d", "r", "u", "l", "dr", "dl", "ur", "ul"]
X_MAS_CROSS = [['M', None, 'M'], [None, 'A', None], ['S', None, 'S']]
X_MAS_REVERSE_CROSS = [['S', None, 'S'], [None, 'A', None], ['M', None, 'M']]
X_MAS_SHIFTED_CROSS = [['S', None, 'M'], [None, 'A', None], ['S', None, 'M']]
X_MAS_REVERSE_SHIFTED_CROSS = [['M', None, 'S'], [None, 'A', None], ['M', None, 'S']]


def search_for_xmas(current_xmas_string, current_x, current_y, current_direction, input_data):
    next_xmas_string = current_xmas_string + input_data[current_y][current_x]

    if next_xmas_string == "XMAS" or next_xmas_string == "SAMX":
        return 1

    if not "XMAS".startswith(next_xmas_string) and not "SAMX".startswith(next_xmas_string):
        return 0

    current_xmas_string = next_xmas_string

    if current_direction == "d" and current_y < len(input_data) - 1:
        return search_for_xmas(current_xmas_string, current_x, current_y + 1, current_direction, input_data)
    if current_direction == "r" and current_x < len(input_data[0]) - 1:
        return search_for_xmas(current_xmas_string, current_x + 1, current_y, current_direction, input_data)
    if current_direction == "u" and current_y > 0:
        return search_for_xmas(current_xmas_string, current_x, current_y - 1, current_direction, input_data)
    if current_direction == "l" and current_x > 0:
        return search_for_xmas(current_xmas_string, current_x - 1, current_y, current_direction, input_data)
    if current_direction == 'dr' and current_x < len(input_data[0]) - 1 and current_y < len(input_data) - 1:
        return search_for_xmas(current_xmas_string, current_x + 1, current_y + 1, current_direction, input_data)
    if current_direction == 'dl' and current_x > 0 and current_y < len(input_data) - 1:
        return search_for_xmas(current_xmas_string, current_x - 1, current_y + 1, current_direction, input_data)
    if current_direction == 'ur' and current_x < len(input_data[0]) - 1 and current_y > 0:
        return search_for_xmas(current_xmas_string, current_x + 1, current_y - 1, current_direction, input_data)
    if current_direction == 'ul' and current_x > 0 and current_y > 0:
        return search_for_xmas(current_xmas_string, current_x - 1, current_y - 1, current_direction, input_data)

    return False


def is_cross(sub_matrix, reference_cross):
    for y in range(3):
        for x in range(3):
            if reference_cross[y][x] is not None and sub_matrix[y][x] != reference_cross[y][x]:
                return False
    return True


def search_for_x_mas(current_x, current_y, input_data):
    sub_matrix = []
    for y in range(current_y - 1, current_y + 2):
        row = []
        for x in range(current_x - 1, current_x + 2):
            if 0 <= y < len(input_data) and 0 <= x < len(input_data[0]):
                row.append(input_data[y][x])
            else:
                row.append(None)
        sub_matrix.append(row)

    if (is_cross(sub_matrix, X_MAS_CROSS)
            or is_cross(sub_matrix, X_MAS_REVERSE_CROSS)
            or is_cross(sub_matrix, X_MAS_SHIFTED_CROSS)
            or is_cross(sub_matrix, X_MAS_REVERSE_SHIFTED_CROSS)):
        return 1
    return 0


def run_part_1():
    input_data = read_input()
    result = 0

    for y in range(len(input_data)):
        for x in range(len(input_data[0])):
            result += sum([search_for_xmas("", x, y, d, input_data) for d in DIRECTIONS])
    return result // 2


def run_part_2():
    input_data = read_input()
    result = 0

    for y in range(len(input_data)):
        for x in range(len(input_data[0])):
            result += search_for_x_mas(x, y, input_data)
    return result


def read_input():
    with open(INPUT_FILE, "r") as file:
        input_data = [l.strip() for l in file.readlines()]

    return [list(l) for l in input_data]


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
