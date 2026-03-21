use reqwest::blocking::{ get, Response, Client };
use derive_builder::Builder;

#[derive(Builder, Debug)]
struct Proxy {
    ip: String,
    port: u16
}

const src: [(&str, fn(&str) -> Vec<Proxy>); 2] = [
    (
        "https://api.proxyscrape.com/v4/free-proxy-list/get?request=display_proxies&proxy_format=ipport&format=text",
        to_proxies
    ),
    (
        "https://spys.me/proxy.txt",
        to_proxies
    )
];

fn parse_proxy(s: &str) -> Proxy {
    let (ip, port) = s
        .split_once(":")
        .unwrap_or_default();
    let port: u16 = port
        .chars()
        .filter(|c| c.is_ascii_digit())
        .collect::<String>()
        .parse()
        .unwrap_or_default();

    ProxyBuilder::default()
        .ip(ip.to_string())
        .port(port)
        .build()
        .unwrap()
}

fn to_proxies(body: &str) -> Vec<Proxy> {
    body.lines().map(parse_proxy).collect::<Vec<_>>()
}

fn get_proxies() -> Vec<Proxy> {
    fn request_proxies((url, f): (&str, fn(&str) -> Vec<Proxy>)) -> Vec<Proxy> {
        let body = get(url)
            .and_then(|r: Response| r.text())
            .unwrap_or_default();

        f(&body)
    }

    src.iter()
       .copied()
       .flat_map(request_proxies)
       .filter(|proxy| !proxy.ip.is_empty() && proxy.port != 0)
       .collect::<Vec<_>>()
}

fn main() {
    //let response: Result<Response, _> = Client::new()
    //    .get("https://api.proxyscrape.com/v4/free-proxy-list/get?request=display_proxies&proxy_format=ipport&format=text")
    //    .header("Content-Type", "text")
    //    .send();
    //
    //let body = response
    //    .and_then(|r| r.text())
    //    .unwrap_or_default();

    dbg!(get_proxies());

    // dbg!("iter123".chars().filter(|c| c.is_ascii_digit()).collect::<String>());
}
