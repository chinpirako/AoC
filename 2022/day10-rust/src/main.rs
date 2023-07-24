fn main() {
    let input = read_input("input.txt");
    use std::time::Instant;
    let now = Instant::now();
    {
        println!("Part 1: {}", solve_part_1(&input));
        solve_part_2(&input);
    }
    let elapsed = now.elapsed();
    println!("Duration: {:.0?}", elapsed)
}

#[inline(always)]
fn read_input(path: &str) -> String {
    use std::fs;
    fs::read_to_string(path).unwrap()
}

fn solve_part_1(input: &String) -> i32 {
    let mut prepared_input = String::new();
    input.lines().for_each(|line| {
        if line.starts_with("addx") {
            prepared_input.push_str(line);
            prepared_input.push_str("\n");
        }
        prepared_input.push_str("noop");
        prepared_input.push_str("\n");
    });
    let mut x = 1;
    let mut current_cycle = 1;
    let mut result = 0;
    prepared_input.lines().for_each(|line| {
        current_cycle += 1;
        if [20, 60, 100, 140, 180, 220].contains(&current_cycle) {
            result += x * current_cycle;
        }
        match line.split_ascii_whitespace().collect::<Vec<&str>>()[..] {
            ["addx", value] => { x += value.parse::<i32>().unwrap() }
            _ => {}
        }
    });

    result
}

fn solve_part_2(input: &String) {
    let mut prepared_input = String::new();
    input.lines().for_each(|line| {
        prepared_input.push_str("noop");
        prepared_input.push_str("\n");
        if line.starts_with("addx") {
            prepared_input.push_str(line);
            prepared_input.push_str("\n");
        }
    });
    let mut x = 1;
    let mut current_cycle = 1;
    let mut line = String::new();
    prepared_input.lines().for_each(|s| {
        let current_pixel = current_cycle % 40 - 1;
        if [x - 1, x, x + 1].contains(&current_pixel) {
            line += "#";
        } else { line += " " }
        current_cycle += 1;
        match s.split_ascii_whitespace().collect::<Vec<&str>>()[..] {
            ["addx", value] => {
                x += value.parse::<i32>().unwrap()
            }
            _ => {}
        }
        if (current_cycle % 40) - 1 == 0 {
            println!("{}", line);
            current_cycle = 1;
            line.clear();
        }
    })
}