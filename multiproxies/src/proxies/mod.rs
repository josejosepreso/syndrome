pub mod handler;
pub mod model;
pub mod scraper;
pub mod config;

use crate::proxies::handler::proxies;
use axum::Router;

pub fn router() -> Router {
    Router::new()
        .route("/proxies", axum::routing::get(proxies))
}
