package com.vang.shareimageazure.beanconfig;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.vang.shareimageazure.constant.Common;
import org.springframework.stereotype.Component;

@Component
public class AzureConfiguation {

    public BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(Common.AZURE_CONNECTION_STRING).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient(Common.AZURE_CONTAINERNAME);
        return container;
    }
}
