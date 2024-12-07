import time

INPUT_FILE = "input.txt"
GUARD_SPRITES = ["v", "^", ">", "<"]


def find_guard_position(input_file):
    for i, row in enumerate(input_file):
        for j, cell in enumerate(row):
            if cell in GUARD_SPRITES:
                return i, j
    return None


def find_obstacles(input_file):
    obstacles = set()
    for i, row in enumerate(input_file):
        for j, cell in enumerate(row):
            if cell == "#":
                obstacles.add((i, j))
    return obstacles


def get_traversed_positions(guard_position, guard_orientation, obstacles, max_x, max_y, traversed_positions=None):
    x, y = guard_position
    if traversed_positions is None:
        traversed_positions = set()
    traversed_positions_list = []

    is_loop = False

    while 0 <= x < max_x and 0 <= y < max_y:
        current_set_size = len(traversed_positions)
        traversed_positions.add((x, y, guard_orientation))
        if len(traversed_positions) == current_set_size:
            is_loop = True
            break
        traversed_positions_list.append((x, y, guard_orientation))

        if guard_orientation == "^":
            if (x - 1, y) in obstacles:
                guard_orientation = ">"
            else:
                x -= 1
        elif guard_orientation == "v":
            if (x + 1, y) in obstacles:
                guard_orientation = "<"
            else:
                x += 1
        elif guard_orientation == ">":
            if (x, y + 1) in obstacles:
                guard_orientation = "v"
            else:
                y += 1
        elif guard_orientation == "<":
            if (x, y - 1) in obstacles:
                guard_orientation = "^"
            else:
                y -= 1

    return traversed_positions, traversed_positions_list, is_loop


def run_part_1():
    input_file = read_input()

    guard_position = find_guard_position(input_file)
    guard_orientation = input_file[guard_position[0]][guard_position[1]]
    obstacles = find_obstacles(input_file)
    max_x = len(input_file[0])
    max_y = len(input_file)

    traversed_positions_with_orientation, _, _ = get_traversed_positions(guard_position, guard_orientation, obstacles,
                                                                         max_x, max_y)
    traversed_positions = set([(x, y) for x, y, _ in traversed_positions_with_orientation])

    return len(traversed_positions)


def run_part_2():
    input_file = read_input()

    guard_position = find_guard_position(input_file)
    guard_orientation = input_file[guard_position[0]][guard_position[1]]
    obstacles = find_obstacles(input_file)
    max_x = len(input_file[0])
    max_y = len(input_file)

    new_obstacles_to_check = []

    traversed_positions_with_orientation, traversed_positions_with_orientation_list, _ = get_traversed_positions(
        guard_position, guard_orientation, obstacles,
        max_x, max_y)

    traversed_positions = set([(x, y) for x, y, _ in traversed_positions_with_orientation])

    import concurrent.futures
    result = 0
    for i in range(max_x):
        for j in range(max_y):
            if (i, j) in obstacles or (i, j) == guard_position or not (i, j) in traversed_positions:
                continue
            else:
                new_obstacles_to_check.append((i, j))

    futures = []
    with concurrent.futures.ThreadPoolExecutor() as executor:
        for i, j in new_obstacles_to_check:
            # Get path before the obstacle
            new_traversed_positions_with_orientation = traversed_positions_with_orientation_list.copy()
            index = 0
            while new_traversed_positions_with_orientation[index][0] != i or \
                    new_traversed_positions_with_orientation[index][1] != j:
                index += 1

            last_guard_position = new_traversed_positions_with_orientation[index - 1]
            guard_position = (last_guard_position[0], last_guard_position[1])
            guard_orientation = last_guard_position[2]

            new_traversed_positions_with_orientation = set(new_traversed_positions_with_orientation[:index - 1])

            # Multiprocessing
            futures.append(
                executor.submit(check_with_new_obstacle, guard_orientation, guard_position, i, j, max_x, max_y,
                                obstacles, new_traversed_positions_with_orientation))

        for future in concurrent.futures.as_completed(futures):
            result += future.result()

    return result


def check_with_new_obstacle(guard_orientation, guard_position, i, j, max_x, max_y, obstacles,
                            new_traversed_positions_with_orientation):
    new_obstacles = obstacles.copy()
    new_obstacles.add((i, j))
    _, _, is_loop = get_traversed_positions(guard_position, guard_orientation, new_obstacles, max_x, max_y,
                                            new_traversed_positions_with_orientation)
    return 1 if is_loop else 0


def read_input():
    with open(INPUT_FILE, "r") as file:
        return [list(l.strip()) for l in file.readlines()]


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
