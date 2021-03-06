package com.google.protobuf.maven;

import com.google.common.collect.ImmutableList;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

/**
 * An abstract base mojo configuration for using {@code protoc} compiler with the main sources.
 *
 * @since 0.3.3
 */
public abstract class AbstractProtocCompileMojo extends AbstractProtocMojo {

    /**
     * The source directories containing the {@code .proto} definitions to be compiled.
     */
    @Parameter(
            required = true,
            defaultValue = "${basedir}/src/main/proto"
    )
    private File protoSourceRoot;

    /**
     * This is the directory into which the (optional) descriptor set file will be created.
     *
     * @since 0.3.0
     */
    @Parameter(
            required = true,
            defaultValue = "${project.build.directory}/generated-resources/protobuf/descriptor-sets"
    )
    private File descriptorSetOutputDirectory;

    @Override
    protected void attachFiles() {
        project.addCompileSourceRoot(getOutputDirectory().getAbsolutePath());
        projectHelper.addResource(project, getProtoSourceRoot().getAbsolutePath(),
                ImmutableList.of("**/*.proto"), ImmutableList.<String>of());
        buildContext.refresh(getOutputDirectory());
    }

    @Override
    protected List<Artifact> getDependencyArtifacts() {
        return project.getCompileArtifacts();
    }

    @Override
    protected File getDescriptorSetOutputDirectory() {
        return descriptorSetOutputDirectory;
    }

    @Override
    protected File getProtoSourceRoot() {
        return protoSourceRoot;
    }
}
