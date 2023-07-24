use std::collections::VecDeque;

struct CrateMovement {
    crates: Vec<VecDeque<char>>,
    moves: Vec<(usize, usize, usize)>,
}

fn main() {
    let input = read_input("input.txt");
    use std::time::Instant;
    let now = Instant::now();
    {
        println!("Part 1: {}", solve_part_1(&input));
        println!("Part 2: {}", solve_part_2(&input));
    }
    let elapsed = now.elapsed();
    println!("Duration: {:.0?}", elapsed)
}

#[inline(always)]
fn read_input(path: &str) -> Vec<String> {
    use std::fs;
    let content = fs::read_to_string(path).unwrap();
    let mut result = Vec::new();
    content.lines().for_each(|x| result.push(x.to_string()));
    result
}

fn solve_part_1(input: &Vec<String>) -> String {
    let mut crate_movement = parse_input(input);
    crate_movement.moves.iter().for_each(|x| {
        let (amount, from, to) = (x.0, x.1, x.2);
        for _ in 0..amount {
            let popped = crate_movement.crates[from - 1].pop_front().unwrap();
            crate_movement.crates[to - 1].push_front(popped);
        }
    });
    let mut result = String::new();
    crate_movement.crates.iter().for_each(|x| { result.push(x[0]) });
    result.clone()
}

fn solve_part_2(input: &Vec<String>) -> String {
    let mut crate_movement = parse_input(input);
    crate_movement.moves.iter().for_each(|x| {
        let (amount, from, to) = (x.0, x.1, x.2);
        let mut tmp_vec = VecDeque::new();
        for _ in 0..amount {
            let popped = crate_movement.crates[from - 1].pop_front().unwrap();
            tmp_vec.push_back(popped);
        }
        for _ in 0..amount {
            let popped = tmp_vec.pop_back().unwrap();
            crate_movement.crates[to - 1].push_front(popped);
        }
    });
    let mut result = String::new();
    crate_movement.crates.iter().for_each(|x| { result.push(x[0]) });
    result.clone()
}

fn parse_input(input: &Vec<String>) -> CrateMovement {
    let mut crates = Vec::new();

    let mut empty_line_index = 0;
    input.iter().position(|x| x.is_empty()).map(|x| empty_line_index = x);
    let crates_line = input[empty_line_index - 1].clone();
    let mut i = 0;
    crates_line.chars().for_each(|x| {
        if x.is_numeric() {
            let mut crate_row = VecDeque::new();
            for j in 0..empty_line_index - 1 {
                let chars = input[j].chars().collect::<Vec<char>>();
                if i < chars.len() && chars[i].is_alphabetic() { crate_row.push_back(chars[i]) }
            }
            crates.push(crate_row);
        }
        i += 1;
    });

    let moves = input[empty_line_index + 1..].iter().map(|x| {
        let x = x.split(" ").collect::<Vec<&str>>();
        let from = x[1].parse::<usize>().unwrap();
        let to = x[3].parse::<usize>().unwrap();
        let crate_number = x[5].parse::<usize>().unwrap();
        (from, to, crate_number)
    }).collect::<Vec<(usize, usize, usize)>>();

    CrateMovement { crates, moves }
}