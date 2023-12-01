use std::cell::RefCell;

#[derive(Clone)]
struct Monkey {
    number: usize,
    starting_items: RefCell<Vec<usize>>,
    operation: String,
    test: usize,
    monkey_if_true: usize,
    monkey_if_false: usize,
    inspected_items: RefCell<usize>,
}

impl Monkey {
    fn new() -> Monkey {
        Monkey {
            number: 0,
            starting_items: RefCell::new(Vec::new()),
            operation: String::new(),
            test: 0,
            monkey_if_true: 0,
            monkey_if_false: 0,
            inspected_items: RefCell::new(0),
        }
    }

    fn inspect(&self) {
        let mut inspected_items = self.inspected_items.borrow_mut();
        *inspected_items += 1;
    }
}

fn main() {
    let input = read_input("input.txt");
    use std::time::Instant;
    let now = Instant::now();
    {
        println!("Part 1: {}", solve_part_1(input.clone()));
        println!("Part 2: {}", solve_part_2(input.clone()));
    }
    let elapsed = now.elapsed();
    println!("Duration: {:.0?}", elapsed)
}

fn read_input(path: &str) -> Vec<Monkey> {
    use std::fs;
    let mut monkeys = Vec::new();
    let mut current_monkey = Monkey::new();
    let content = fs::read_to_string(path).unwrap();
    for line in content.lines() {
        let trimmed_line = line.trim_start();
        if trimmed_line.starts_with("Monkey") {
            let current_monkey_number = trimmed_line
                .split_ascii_whitespace()
                .collect::<Vec<&str>>()[1]
                .trim_end_matches(':')
                .parse::<usize>()
                .unwrap();
            current_monkey.number = current_monkey_number;
        }
        if trimmed_line.starts_with("Starting items") {
            let starting_items = trimmed_line
                .split(": ")
                .collect::<Vec<&str>>()[1]
                .split(", ")
                .map(|s| s.parse::<usize>().unwrap())
                .collect::<Vec<usize>>();
            current_monkey.starting_items = RefCell::new(starting_items);
        }
        if trimmed_line.starts_with("Operation") {
            let operation = trimmed_line
                .split(": new =")
                .collect::<Vec<&str>>()[1]
                .to_string();
            current_monkey.operation = operation.trim_start().to_string();
        }
        if trimmed_line.starts_with("Test") {
            let divisible_by = trimmed_line
                .split(": ")
                .collect::<Vec<&str>>()[1];
            match divisible_by.split_ascii_whitespace().collect::<Vec<&str>>()[..] {
                [_, _, divisible_number] => {
                    let parsed_divisible_number = divisible_number
                        .parse::<usize>()
                        .unwrap();
                    current_monkey.test = parsed_divisible_number;
                }
                _ => ()
            }
        }
        if trimmed_line.starts_with("If") {
            let throw_instruction = trimmed_line
                .split(": ")
                .collect::<Vec<&str>>()[1];
            match throw_instruction.split_ascii_whitespace().collect::<Vec<&str>>()[..] {
                [_, _, _, monkey_number] => {
                    let parsed_monkey_number = monkey_number
                        .parse::<usize>()
                        .unwrap();
                    if trimmed_line.contains("true") {
                        current_monkey.monkey_if_true = parsed_monkey_number;
                    } else {
                        current_monkey.monkey_if_false = parsed_monkey_number;
                        monkeys.push(current_monkey.clone());
                        current_monkey = Monkey::new();
                    }
                }
                _ => {}
            }
        }
    }
    monkeys
}

fn solve_part_1(input: Vec<Monkey>) -> usize {
    simulate_rounds(input, 20, 1)
}

fn solve_part_2(input: Vec<Monkey>) -> usize {
    simulate_rounds(input, 10000, 2)
}

fn simulate_rounds(input: Vec<Monkey>, rounds: usize, part: usize) -> usize {
    let reductor = if part == 1 { 3 } else { input.iter().map(|monkey| monkey.test).product() };
    for _ in 1..rounds + 1 {
        for monkey in input.iter() {
            for item in monkey.starting_items.borrow().iter() {
                let mut new_worry_level = do_operation(&monkey.operation, *item);
                new_worry_level = if part == 1 { new_worry_level / reductor } else { new_worry_level % reductor };
                if new_worry_level % monkey.test == 0 {
                    input[monkey.monkey_if_true].starting_items.borrow_mut().push(new_worry_level);
                } else {
                    input[monkey.monkey_if_false].starting_items.borrow_mut().push(new_worry_level);
                }
                monkey.inspect();
            }
            monkey.starting_items.borrow_mut().clear();
        }
    }
    let mut inspections = input.iter()
        .map(|monkey| monkey.inspected_items.borrow().clone())
        .collect::<Vec<usize>>();
    inspections.sort_by(|a, b| b.cmp(a));
    inspections.iter().take(2).product()
}

#[inline(always)]
fn do_operation(operation: &String, item: usize) -> usize {
    return match operation.split_ascii_whitespace().collect::<Vec<&str>>()[..] {
        ["old", operand, number] => {
            let parsed_number = if number == "old" { item } else { number.parse::<usize>().unwrap() };
            match operand {
                "+" => item + parsed_number,
                "-" => item - parsed_number,
                "*" => item * parsed_number,
                "/" => item / parsed_number,
                _ => 0
            }
        }
        _ => 0
    };
}