use serde::Serialize;
use derive_builder::Builder;
use::std::fmt;

#[derive(Builder, Debug, Serialize)]
pub struct Proxy {
    pub ip: String,
    pub port: u16,
    source: String,
}

impl fmt::Display for Proxy {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}:{}", self.ip, self.port)
    }
}
