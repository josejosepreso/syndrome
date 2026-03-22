use axum::Json;
use crate::proxies::model::Proxy;
use crate::proxies::scraper::get_proxies;

pub async fn proxies() -> Json<Vec<Proxy>> {
    let proxies = tokio::task::spawn_blocking(|| { get_proxies() })
        .await
        .unwrap_or_default();

    Json(proxies)
}
