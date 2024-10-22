# Use an official Node.js runtime as a parent image
FROM node:23

# Set the working directory in the container
WORKDIR /client

# Copy the package.json and pnpm-lock.yaml files to the container
COPY client/package.json client/pnpm-lock.yaml ./

# Install pnpm
RUN npm install -g pnpm

# Install dependencies
RUN pnpm install

# Copy the rest of the application code to the container
COPY client/ ./

# Build the application
RUN pnpm run build

# Expose the port the app runs on
EXPOSE 3000

# Define the command to run the app
CMD ["pnpm", "run", "dev"]