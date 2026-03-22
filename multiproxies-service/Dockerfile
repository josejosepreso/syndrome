FROM rust:latest

RUN cargo new --bin multiproxies-service
WORKDIR ./multiproxies-service
COPY ./Cargo.toml ./Cargo.toml
COPY ./src ./src
RUN cargo build --release

CMD ["./target/release/multiproxies-service"]
