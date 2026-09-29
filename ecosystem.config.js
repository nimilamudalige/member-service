module.exports = {
  apps: [
    {
      name: "member-service",
      script: "java",
      args: "-jar member-service.jar",
      cwd: "/opt/pulsefit/member-service",
      env: {
        SERVER_PORT: "8081",
        CONFIG_SERVER_URL: "http://localhost:8888",
        EUREKA_SERVER_URL: "http://localhost:8761/eureka",
        DB_HOST: "<CLOUD_SQL_PRIVATE_IP>",
        DB_PORT: "3306",
        DB_NAME: "pulsefit_member_db",
        DB_USERNAME: "<DB_USERNAME>",
        DB_PASSWORD: "<DB_PASSWORD>",
        GCP_PROJECT_ID: "<YOUR_GCP_PROJECT_ID>",
        GCS_BUCKET_NAME: "<YOUR_GCP_PROJECT_ID>-member-photos"
      },
      autorestart: true,
      max_restarts: 10,
      min_uptime: "10s",
      restart_delay: 3000,
      out_file: "/var/log/pm2/member-service-out.log",
      error_file: "/var/log/pm2/member-service-error.log",
      log_date_format: "YYYY-MM-DD HH:mm:ss"
    }
  ]
};
