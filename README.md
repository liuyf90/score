# record-the-points

#### 项目介绍
  本项目产生于项目组的管理需求，适用于20人以下的软件研发项目组实用。本项目有以下特点：
    1、采用积分驱动，任务从创建到完成、测试，不同角色在任务的不同阶段会生成不同的积分，通过积分可以测算开发人员的效率及排名。
    2、任务采用委派及自由领取两种方式，自己领取的话会有额外积分
    3、任务的工时可以自己承诺，也可以由项目经理委派。超时有扣分，提前有加分。
    4、任务池，任务会首先进入任务池，开发人员可以在任务池中领取任务，如果无人领取，则可以直接强制委派给开发人员。

#### 软件架构
    软件主要技术栈说明：
      springboot
      jpa
      thymeleaf
    开发环境：
      jdk8
      mysql8.0
      mavn

#### 安装教程

  1、数据库脚本，项目目录下score.sql
  2、项目下载到本地，创建score数据库，执行score.sql
  3、项目目录下执行
    > mvn spring-boot:run

#### 使用说明

1. 测试帐户  liuyf / 528726

![image](https://user-images.githubusercontent.com/25540464/147533362-9f39db73-32f1-4d7f-9f9a-6a799cdcb966.png)
![image](https://user-images.githubusercontent.com/25540464/147533417-2dfb2cd6-e7c1-4171-8c86-433f5ea2723e.png)
![image](https://user-images.githubusercontent.com/25540464/147533500-44e9d2aa-e271-4655-ab5d-64077fd6db18.png)
![image](https://user-images.githubusercontent.com/25540464/147533554-756cfad3-bc42-46fb-87f9-8b11c1c941d2.png)



