use scraper::{Html, Selector, ElementRef};

use crate::proxies::model::{Proxy, ProxyBuilder};
use crate::proxies::config::SRC;
use crate::util::get_body;

pub fn parse_table(url: &str, body: &str) -> Vec<Proxy> {
    let html = Html::parse_document(body);

    let tbl_selector = Selector::parse("table").unwrap();
    let tr_selector = Selector::parse("tr").unwrap();
    let td_selector = Selector::parse("td").unwrap();

    let proxies_html_tbl = match html.select(&tbl_selector).next() {
        Some(t) => t,
        None => return Vec::new(),
    };

    let get_rows = |tr: ElementRef| {
        tr.select(&td_selector)
          .map(|td: ElementRef| td.text().collect::<String>())
          .collect::<Vec<_>>()
    };

    let to_proxy = |v: Vec<String>| {
        if v.len() < 2 {
            return None;
        }

        let ip = v[0].clone();
        let port = v[1].parse::<u16>().unwrap_or_default();

        ProxyBuilder::default()
            .ip(ip)
            .port(port)
            .source(url.to_string())
            .build()
            .ok()
    };

    proxies_html_tbl
        .select(&tr_selector)
        .map(get_rows)
        .filter_map(to_proxy)
        .collect()
}

pub fn to_proxies(url: &str, body: &str) -> Vec<Proxy> {
    let parse_proxy = |s: &str| {
        let (ip, port) = s
            .split_once(":")
            .unwrap_or_default();
        let port = port
            .chars()
            .filter(|c| c.is_ascii_digit())
            .collect::<String>()
            .parse::<u16>()
            .unwrap_or_default();

        ProxyBuilder::default()
            .ip(ip.to_string())
            .port(port)
            .source(url.to_string())
            .build()
            .unwrap()
    };

    body.lines()
        .map(parse_proxy)
        .collect::<Vec<_>>()
}

fn request_proxies((url, f): (&str, fn(&str, &str) -> Vec<Proxy>)) -> Vec<Proxy> {
    f(url, &get_body(url))
}

pub fn get_proxies() -> Vec<Proxy> {
    SRC.iter()
       .copied()
       .flat_map(request_proxies)
       .filter(|proxy| !proxy.ip.is_empty() && proxy.port != 0)
       .collect::<Vec<_>>()
}
