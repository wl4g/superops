package com.wl4g.devops.ci.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.wl4g.devops.tool.common.lang.Assert2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

/**
 * @author vjay
 * @date 2020-04-23 11:14:00
 */
public class DockerJavaUtil {

    public static DockerClient sampleConnect(String serverUrl) {
        return DockerClientBuilder.getInstance(serverUrl).build();
    }

    /**
     * 更高级的连接方式，后续使用证书需要用到
     *
     * @return
     */
    public static DockerClient advanceConnect() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2376")
                //TODO 后续支持证书
                .withDockerTlsVerify(true)
                .withDockerCertPath("/home/user/.docker/certs")//证书？
                .withDockerConfig("/Users/vjay/.docker")
                .withApiVersion("1.30") // optional
                .withRegistryUrl("https://index.docker.io/v1/")//填私库地址
                .withRegistryUsername("username")//填私库用户名
                .withRegistryPassword("123456")//填私库密码
                .withRegistryEmail("username@github.com")//填私库注册邮箱
                .build();
        return DockerClientBuilder.getInstance(config).build();
    }


    /**
     * @param client
     * @param tarPath
     * @param dockerTemplate
     * @param appBinName
     * @param args
     * @return
     * @throws IOException
     */
    public static String buildImage(DockerClient client, Set<String> tags, File workSpace, File dockerTemplate, Map<String, String> args) throws IOException {
        copyFile2WorkSpace(workSpace, dockerTemplate);

        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                System.out.println("" + item);
                super.onNext(item);
            }
        };
        BuildImageCmd buildImageCmd = client.buildImageCmd(workSpace).withTags(tags);
        for (Map.Entry<String, String> entry : args.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            buildImageCmd.withBuildArg(key, value);
        }
        return buildImageCmd.exec(callback).awaitImageId();
    }

    /**
     * @param tarPath
     * @param dockerTemplate
     * @param appBinName
     * @return
     * @throws IOException
     */
    private static void copyFile2WorkSpace(File workSpace, File dockerTemplate) throws IOException {//为什么要把文件复制出来？因为COPY failed: Forbidden path outside the build context，dockerfile不允许使用上下文外的文件
        //String property = System.getProperty("user.home");
        //File workspace = new File(property + "/tmp/docker_file_workspace/" + System.currentTimeMillis());
        if (!workSpace.exists()) {
            workSpace.mkdirs();
        }
        Assert2.isTrue(workSpace.exists(), "create dir fail");
        String property = System.getProperty("user.dir");
        //Files.copy(tarPath.toPath(), new File(workspace.getCanonicalPath() + "/" + appBinName + ".tar").toPath());
        Files.copy(new File(dockerTemplate.getCanonicalPath()).toPath(), new File(workSpace.getCanonicalPath() + "/Dockerfile").toPath());
    }


    /**
     * 创建容器
     *
     * @param client
     * @return
     */
    public static CreateContainerResponse createContainers(DockerClient client, String containerName, String imageName, Map<Integer, Integer> ports) {//TODO 优化

        //TODO 处理端口映射
        List<ExposedPort> exposedPorts = new ArrayList<>();
        Ports portBindings = new Ports();
        for (Map.Entry<Integer, Integer> entry : ports.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            ExposedPort exposedPort = ExposedPort.tcp(key);
            exposedPorts.add(exposedPort);
            portBindings.bind(exposedPort, Ports.Binding.bindPort(value));
        }
        HostConfig hostConfig = newHostConfig().withPortBindings(portBindings);

        return client.createContainerCmd(imageName)
                .withName(containerName)
                .withHostConfig(hostConfig)
                .withExposedPorts(exposedPorts).exec();

    }


    /**
     * 启动容器
     *
     * @param client
     * @param containerId
     */
    public static void startContainer(DockerClient client, String containerId) {
        client.startContainerCmd(containerId).exec();
    }

    /**
     * 启动容器
     *
     * @param client
     * @param containerId
     */
    public static void stopContainer(DockerClient client, String containerId) {
        client.stopContainerCmd(containerId).exec();
    }

    /**
     * 删除容器
     *
     * @param client
     * @param containerId
     */
    public static void removeContainer(DockerClient client, String containerId) {
        client.removeContainerCmd(containerId).exec();
    }


}
