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
    obstacles = []
    for i, row in enumerate(input_file):
        for j, cell in enumerate(row):
            if cell == "#":
                obstacles.append((i, j))
    return obstacles


def get_traversed_positions(guard_position, guard_orientation, obstacles, max_x, max_y, traversed_positions=None):
    if traversed_positions is None:
        traversed_positions = []
    x, y = guard_position

    is_loop = False
    has_changed_direction = False

    while 0 <= x < max_x and 0 <= y < max_y:
        if not has_changed_direction:
            if (x, y, guard_orientation) in traversed_positions:
                is_loop = True
                break
            traversed_positions.append((x, y, guard_orientation))
        has_changed_direction = False

        if guard_orientation == "^":
            if (x - 1, y) in obstacles:
                guard_orientation = ">"
                has_changed_direction = True
            else:
                x -= 1
        elif guard_orientation == "v":
            if (x + 1, y) in obstacles:
                guard_orientation = "<"
                has_changed_direction = True
            else:
                x += 1
        elif guard_orientation == ">":
            if (x, y + 1) in obstacles:
                guard_orientation = "v"
                has_changed_direction = True
            else:
                y += 1
        elif guard_orientation == "<":
            if (x, y - 1) in obstacles:
                guard_orientation = "^"
                has_changed_direction = True
            else:
                y -= 1

    return traversed_positions, is_loop


def run_part_1():
    input_file = read_input()

    guard_position = find_guard_position(input_file)
    guard_orientation = input_file[guard_position[0]][guard_position[1]]
    obstacles = find_obstacles(input_file)
    max_x = len(input_file[0])
    max_y = len(input_file)

    traversed_positions_with_orientation, _ = get_traversed_positions(guard_position, guard_orientation, obstacles,
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

    traversed_positions_with_orientation, _ = get_traversed_positions(guard_position, guard_orientation, obstacles,
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

    with concurrent.futures.ThreadPoolExecutor() as executor:
        for i, j in new_obstacles_to_check:
            # Get path before the obstacle
            new_traversed_positions_with_orientation = traversed_positions_with_orientation.copy()
            index = 0
            while new_traversed_positions_with_orientation[index][0] != i or \
                    new_traversed_positions_with_orientation[index][1] != j:
                index += 1

            last_guard_position = new_traversed_positions_with_orientation[index - 1]
            guard_position = (last_guard_position[0], last_guard_position[1])
            guard_orientation = last_guard_position[2]

            new_traversed_positions_with_orientation = new_traversed_positions_with_orientation[:index - 1]
            # Multiprocessing
            future = executor.submit(check_with_new_obstacle, guard_orientation, guard_position, i, j, max_x,
                                     max_y, obstacles, new_traversed_positions_with_orientation)
            result += future.result()

    return result


def check_with_new_obstacle(guard_orientation, guard_position, i, j, max_x, max_y, obstacles, traversed_positions=None):
    new_obstacles = obstacles.copy()
    new_obstacles.append((i, j))
    print(f"Checking obstacle at {i}, {j}")
    _, is_loop = get_traversed_positions(guard_position, guard_orientation, new_obstacles, max_x, max_y,
                                         traversed_positions)
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
