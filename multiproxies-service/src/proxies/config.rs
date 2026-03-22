use crate::proxies::scraper::{ to_proxies, parse_table };
use crate::proxies::model::Proxy;

pub const SRC: [(&str, fn(&str, &str) -> Vec<Proxy>); 3] = [
    (
        "https://api.proxyscrape.com/v4/free-proxy-list/get?request=display_proxies&proxy_format=ipport&format=text",
        to_proxies
    ),
    (
        "https://spys.me/proxy.txt",
        to_proxies
    ),
    (
        "https://free-proxy-list.net/en/",
        parse_table
    )
];
