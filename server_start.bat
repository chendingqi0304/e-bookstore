@echo off
set KAFKA_HOME=C:\kafka_2.13-3.8.0
set ZOOKEEPER_HOME=C:\apache-zookeeper-3.6.4-bin
set REDIS_HOME=C:\Redis
REM 启动第一个 PowerShell 窗口并运行 zkServer

powershell -Command "Start-Process PowerShell -ArgumentList '-NoExit', '-Command', \"cd '$env:ZOOKEEPER_HOME'; zkServer\" -Verb RunAs"


REM 等待几秒钟以确保第一个命令已经启动
timeout /t 5 /nobreak >nul

REM 启动第二个 PowerShell 窗口并运行 Redis 
start powershell -NoExit -Command "cd %REDIS_HOME%; .\redis-server.exe  redis.windows.conf"

REM 启动第三个 PowerShell 窗口并运行 Kafka 服务器
start powershell -NoExit -Command "cd %KAFKA_HOME%; .\bin\windows\kafka-server-start.bat .\config\server.properties"