# Define variables
DOCKER_IMAGE_NAME = client
DOCKER_CONTAINER_NAME = client-container
DOCKERFILE_PATH = ./Dockerfile
PORT = 3000

# Build the Docker image
build:
	docker build -t $(DOCKER_IMAGE_NAME) -f $(DOCKERFILE_PATH) .

# Run the Docker container
jan:
	docker rm $(DOCKER_CONTAINER_NAME)
	docker run -p $(PORT):$(PORT) --name $(DOCKER_CONTAINER_NAME) $(DOCKER_IMAGE_NAME)

# Stop the Docker container
stop:
	docker stop $(DOCKER_CONTAINER_NAME)

# Clean up Docker images and containers
clean:
	docker rmi $(DOCKER_IMAGE_NAME) || true
	docker rm $(DOCKER_CONTAINER_NAME) || true

# Run the Next.js project locally
dev:
	cd client && pnpm run dev

# Build the Next.js project
build-next:
	cd client && pnpm run build

# Lint the Next.js project
lint:
	cd client && pnpm run lint