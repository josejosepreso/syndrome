use reqwest::blocking::{ get, Response };

pub fn get_body(url: &str) -> String {
    get(url).and_then(|r: Response| r.text()).unwrap_or_default()
}
