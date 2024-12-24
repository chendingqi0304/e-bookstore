@echo off
set SPARK_HOME=C:\spark-3.5.4-bin-hadoop3
REM 启动 PowerShell 窗口并运行 spark
start powershell -NoExit -Command "cd %SPARK_HOME%; .\sbin\start-master.shr; .\sbin\start-worker.sh spark://localhost.local:7077"