import time

INPUT_FILE = "input.txt"


class Tree(object):
    def __init__(self, label, value, children=None):
        self.label = label
        self.value = value
        self.children = set()
        if children is not None:
            for child in children:
                self.add_child(child)

    def add_child(self, node):
        assert isinstance(node, Tree)
        self.children.add(node)


def find_level_indexes(input_data, param):
    level_indexes = []
    for y in range(len(input_data)):
        for x in range(len(input_data[0])):
            if input_data[y][x] == param:
                level_indexes.append((x, y))
    return level_indexes


def get_trees_at_level(tree, level):
    if level == 0:
        return [tree]
    else:
        trees = []
        for child in tree.children:
            trees.extend(get_trees_at_level(child, level - 1))
        return trees


def is_adjacent(source, destination):
    if source[0] == destination[0] and source[1] == destination[1]:
        return False
    if source[0] == destination[0] and abs(source[1] - destination[1]) == 1:
        return True
    if source[1] == destination[1] and abs(source[0] - destination[0]) == 1:
        return True
    return False


def count_distinct_values_in_tree(tree, label):
    distinct_values_for_label = set()
    if tree.label == label:
        distinct_values_for_label.add(tree.value)
    for child in tree.children:
        distinct_values_for_label |= count_distinct_values_in_tree(child, label)
    return distinct_values_for_label


def run_part_1():
    input_data = read_input()
    result = 0
    trees = generate_path_trees(input_data)
    result += sum(len(count_distinct_values_in_tree(tree, 9)) for tree in trees)
    return result


def generate_path_trees(input_data):
    level_0_indexes = find_level_indexes(input_data, 0)
    trees = set()
    for level_i_index in level_0_indexes:
        tree = Tree(0, level_i_index)
        trees.add(tree)
    current_trees = trees
    for i in range(1, 10):
        level_i_indexes = set(find_level_indexes(input_data, i))
        new_trees = set()
        for tree in current_trees:
            adjacent_indexes = {idx for idx in level_i_indexes if is_adjacent(tree.value, idx)}
            for idx in adjacent_indexes:
                tree.add_child(Tree(i, idx))
            new_trees.update(tree.children)
        current_trees = new_trees
    return trees


def count_distinct_paths_to_label(tree, label):
    if tree.label == label:
        return tree.value
    distinct_values = list()
    for child in tree.children:
        distinct_values.extend(count_distinct_paths_to_label(child, label))
    return distinct_values


def run_part_2():
    input_data = read_input()
    result = 0
    trees = generate_path_trees(input_data)
    # Cba wondering why I have to divide by 2 (expand being fucky wucky)
    result += sum(len(count_distinct_paths_to_label(tree, 9)) // 2 for tree in trees)
    return result


def read_input():
    input_data = []
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        for line in lines:
            input_data.append([int(l) if l != '.' else l for l in list(line.strip())])
    return input_data


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
