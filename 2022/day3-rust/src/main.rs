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
    let content = fs::read_to_string(path).expect("Cannot read to string for some ungodly reason");
    let mut result: Vec<String> = Vec::new();
    content.lines().for_each(|x| result.push(x.to_string()));
    result
}

#[inline(always)]
fn solve_part_1(input: &Vec<String>) -> u16 {
    let mut result: u16 = 0;
    input.iter().for_each(|x| {
        let part_1: &str = &x[0..x.len() / 2];
        let part_2: &str = &x[x.len() / 2..x.len()];
        let intersection = part_1.chars()
            .filter(|x| part_2.contains(*x))
            .collect::<Vec<char>>();
        result += compute_priority(&intersection[0].to_string());
    });
    result
}

#[inline(always)]
fn solve_part_2(input: &Vec<String>) -> u16 {
    let mut result: u16 = 0;
    let mut tmp: Vec<String> = Vec::new();
    let mut i: usize = 0;
    input.iter().for_each(|x| {
        tmp.push(x.to_string());
        if (i + 1) % 3 == 0 {
            let intersection = tmp[0].chars()
                .filter(|x| tmp[1].contains(*x) && tmp[2].contains(*x))
                .collect::<Vec<char>>();
            result += compute_priority(&intersection[0].to_string());
            tmp.clear();
        }
        i += 1;
    });
    result
}

#[inline(always)]
fn compute_priority(letter: &str) -> u16 {
    return if LOWER_CASE.contains(letter) {
        1 + (LOWER_CASE.find(letter).unwrap() as u16)
    } else {
        27 + (UPPER_CASE.find(letter).unwrap() as u16)
    };
}

const LOWER_CASE: &str = "abcdefghijklmnopqrstuvwxyz";
const UPPER_CASE: &str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";