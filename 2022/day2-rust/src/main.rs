use std::collections::HashMap;

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
    compute_score(input, 1)
}

#[inline(always)]
fn solve_part_2(input: &Vec<String>) -> u16 {
    compute_score(input, 2)
}

#[inline(always)]
fn compute_score(input: &Vec<String>, matchup: i8) -> u16 {
    let matchups_part_1: HashMap<&str, u16> = HashMap::from([
        ("A X", 4),
        ("A Y", 8),
        ("A Z", 3),
        ("B X", 1),
        ("B Y", 5),
        ("B Z", 9),
        ("C X", 7),
        ("C Y", 2),
        ("C Z", 6)
    ]);

    let matchups_part_2: HashMap<&str, u16> = HashMap::from([
        ("A X", 3),
        ("A Y", 4),
        ("A Z", 8),
        ("B X", 1),
        ("B Y", 5),
        ("B Z", 9),
        ("C X", 2),
        ("C Y", 6),
        ("C Z", 7)
    ]);
    let mut score: u16 = 0;
    let matchups = if matchup == 1 { matchups_part_1 } else { matchups_part_2 };
    input.iter().for_each(|x| score += matchups.get(x.as_str()).unwrap());
    score
}