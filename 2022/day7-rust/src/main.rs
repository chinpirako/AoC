#[derive(Debug, Eq, PartialEq, PartialOrd, Ord)]
struct Folder {
    name: String,
    size: usize,
    parent: usize,
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

fn read_input(path: &str) -> Vec<Folder> {
    use std::fs;
    let content = fs::read_to_string(path).unwrap();
    let mut result = vec![Folder {
        name: "/".to_string(),
        size: 0,
        parent: 0,
    }];

    let root_directory_index = 0;
    let mut current_directory_index = 0;

    for line in content.lines() {
        match line.split_ascii_whitespace().collect::<Vec<&str>>()[..] {
            ["$", "ls"] => {}
            ["dir", _] => {}
            ["$", "cd", name] => {
                match name {
                    "/" => current_directory_index = root_directory_index,
                    ".." => current_directory_index = result[current_directory_index].parent,
                    _ => {
                        result.push(Folder {
                            name: name.to_string(),
                            size: 0,
                            parent: current_directory_index,
                        });
                        current_directory_index = result.len() - 1;
                    }
                }
            }
            [size, _] => {
                let mut temp = current_directory_index;
                loop {
                    let size = size.parse::<usize>().unwrap();
                    result[temp].size += size;
                    if temp == root_directory_index {
                        break;
                    }
                    temp = result[temp].parent;
                }
            }
            _ => {}
        }
    }
    result
}

#[inline(always)]
fn solve_part_1(input: &[Folder]) -> usize {
    input.iter().map(|x| x.size).filter(|x| *x < 100000).sum()
}

#[inline(always)]
fn solve_part_2(input: &[Folder]) -> usize {
    let total_disk_size: usize = 70000000;
    let required_update_size: usize = 30000000;

    let current_size = input[0].size;
    let current_unused_size = total_disk_size - current_size;
    let size_to_free = required_update_size - current_unused_size;

    let oversize_folders = input.iter()
        .filter(|x| x.size >= size_to_free)
        .min_by(|a, b| a.size.cmp(&b.size))
        .unwrap();

    oversize_folders.size
}




