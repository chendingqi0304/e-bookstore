@echo off
set KAFKA_HOME=C:\kafka_2.13-3.8.0
set ZOOKEEPER_HOME=C:\apache-zookeeper-3.6.4-bin
REM 启动第一个 PowerShell 窗口并运行 zkServer

powershell -Command "Start-Process PowerShell -ArgumentList '-NoExit', '-Command', \"cd '$env:ZOOKEEPER_HOME'; zkServer\" -Verb RunAs"
REM 等待几秒钟以确保第一个命令已经启动
timeout /t 10 /nobreak >nul

REM 启动第二个 PowerShell 窗口并运行 Kafka 服务器
start powershell -NoExit -Command "cd %KAFKA_HOME%; .\bin\windows\kafka-server-start.bat .\config\server.properties"