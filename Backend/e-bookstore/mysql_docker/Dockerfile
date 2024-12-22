FROM mysql:8.0

# 设置环境变量
ENV MYSQL_ROOT_PASSWORD=Chen20040304
ENV MYSQL_DATABASE=e-bookstore
# 设置工作目录
WORKDIR /var/lib/mysql

# 更改目录权限
RUN chown -R mysql:mysql /var/lib/mysql

# 执行MySQL镜像的启动命令
CMD ["mysqld"]

# 复制初始化 SQL 文件到容器
COPY ./mysql_docker.sql /docker-entrypoint-initdb.d/

COPY init.sh /init.sh
RUN chmod +x /init.sh

CMD ["/init.sh"]
