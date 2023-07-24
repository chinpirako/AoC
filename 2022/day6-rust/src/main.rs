use std::collections::HashSet;

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
fn read_input(path: &str) -> String {
    use std::fs;
    fs::read_to_string(path).unwrap()
}

#[inline(always)]
fn solve_part_1(input: &String) -> usize {
    find_marker_index(input, 4)
}

#[inline(always)]
fn solve_part_2(input: &String) -> usize {
    find_marker_index(input, 14)
}

#[inline(always)]
fn find_marker_index(input: &String, x: usize) -> usize {
    let mut result = 0;
    loop {
        let substring: Vec<char> = input.chars().skip(result).take(x).collect();
        let unique_chars: HashSet<_> = substring.iter().cloned().collect();
        if unique_chars.len() == x {
            result += x;
            break;
        }
        result += 1;
    }
    result
}