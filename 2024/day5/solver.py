import time

INPUT_FILE = "input.txt"


def run_part_1():
    page_orders, page_updates = read_input()
    result = 0

    for page_update in page_updates:
        is_ordered = check_if_is_ordered(page_orders, page_update)
        if is_ordered:
            middle_index = len(page_update) // 2
            middle_page = page_update[middle_index]
            result += int(middle_page)

    return result


def check_if_is_ordered(page_orders, page_update):
    is_ordered = True
    for j in range(len(page_update) - 1):
        current_page = page_update[j]
        page_order = page_orders.get(current_page, [])
        is_in_order = len([p for p in page_update[j + 1:] if p in page_order]) == len(page_update[j + 1:])
        if not is_in_order:
            is_ordered = False
            break
    return is_ordered


def run_part_2():
    page_orders, page_updates = read_input()
    result = 0
    unordered_pages = []
    for i in range(len(page_updates)):
        page_update = page_updates[i]
        for j in range(len(page_update) - 1):
            current_page = page_update[j]
            page_order = page_orders.get(current_page, {})
            is_in_order = len([p for p in page_update[j + 1:] if p in page_order]) == len(page_update[j + 1:])
            if not is_in_order:
                unordered_pages.append(page_update)
                break

    for page_update in unordered_pages:
        while not check_if_is_ordered(page_orders, page_update):
            for j in range(len(page_update) - 1):
                current_page = page_update[j]
                page_order = page_orders.get(current_page, {})
                if page_order == {}:
                    page_update.remove(current_page)
                    page_update.append(current_page)
                else:
                    out_of_order_pages = [p for p in page_update[j + 1:] if p not in page_order]
                    for out_of_order_page in out_of_order_pages:
                        page_update.remove(out_of_order_page)
                        page_update.insert(j, out_of_order_page)

        middle_index = len(page_update) // 2
        middle_page = page_update[middle_index]
        result += int(middle_page)

    return result


def read_input():
    with open(INPUT_FILE, "r") as file:
        lines = file.readlines()
        page_orders = {}
        page_orders_list = [l.strip().split('|') for l in lines if '|' in l]
        for page_order_pair in page_orders_list:
            if page_order_pair[0] not in page_orders:
                page_orders[page_order_pair[0]] = [page_order_pair[1]]
            else:
                page_orders[page_order_pair[0]].append(page_order_pair[1])
        page_updates = [l.strip().split(',') for l in lines if ',' in l]

    return page_orders, page_updates


if __name__ == "__main__":
    start_time = time.time()
    print(run_part_1())
    print(f"--- {time.time() - start_time} seconds ---")
    print(run_part_2())
    print(f"--- {time.time() - start_time} seconds ---")
