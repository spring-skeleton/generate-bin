package com.codeages.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.text.MessageFormat;

@Mojo(name = "generate-bin")
public class GenerateBinMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.build.directory}")
    private String projectBuildDir;

    @Parameter(defaultValue = "${project.name}")
    private String projectName;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            String[] files = new String[] {"start.sh", "stop.sh"};
            String folder = projectBuildDir + File.separator + projectName + File.separator + "bin";
            for (String fileName:files) {
                String resoureName = "/bin_template/"+fileName;

                InputStream is = getClass().getResourceAsStream(resoureName);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuffer sb=new StringBuffer("");
                String s = "";
                while((s=br.readLine())!=null) {
                    sb.append(s).append("\n");
                }
                br.close();
                is.close();

                String command = sb.toString();
                if(fileName.equals("start.sh")){
                    command = MessageFormat.format(sb.toString(), projectName);
                }

                File newFile = createFile(folder, fileName);
                newFile.setExecutable(true);
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                fileOutputStream.write(command.getBytes());
                fileOutputStream.close();
                getLog().info(String.format("把文件 %s copy 到 %s", resoureName, folder));
            }
        }catch (Exception e) {
            getLog().info(e.getMessage());
        }
    }

    private File createFile(String folder, String fileName) throws IOException {
        File newFile = new File(folder+File.separator+fileName);
        if(!newFile.getParentFile().exists() && !newFile.getParentFile().isDirectory()) {
            newFile.getParentFile().mkdirs();
        }

        if (!newFile.exists()) {
            newFile.createNewFile();
        } else{
            newFile.delete();
            newFile.createNewFile();
        }
        return newFile;
    }


}
