package com.example.sshconnection.ui.object;

public class SSHCommandObject {
  private int id;
  private String name;
  private String host;
  private String port;
  private String username;
  private String password;
  private String command;
  private String createdAt;
  private String updatedAt;
  private int status;

  public SSHCommandObject() {
  }

  public SSHCommandObject(int id, String name, String host, String port, String username,
      String password, String command, String createdAt, String updatedAt, int status) {
    this.id = id;
    this.name = name;
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
    this.command = command;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
