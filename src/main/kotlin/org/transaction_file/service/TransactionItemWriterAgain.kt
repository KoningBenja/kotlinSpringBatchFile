package org.transaction_file.service

import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.transaction_file.domain.model.Transaction
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.core.io.FileSystemResource
import java.io.Writer

class TransactionItemWriterAgain :
FlatFileItemWriter<Transaction>() {
    private lateinit var outputPath: String

    fun setOutputPath(outputPath: String) {
        this.outputPath = outputPath
    }

    init {
        this.setName("TransactionItemWriter")
        this.setHeaderCallback(TransactionFileHeaderCallback())
        this.setLineAggregator(TransactionalLineAggregator())
    }

    override fun open(writer: ExecutionContext) {
        this.setResource(FileSystemResource(outputPath))
        super.open(writer)
    }
}

class TransactionalLineAggregatorAgain :
DelimitedLineAggregator<Transaction>() {
    init {
        this.apply {
            setDelimiter(",")
            setFieldExtractor(
                BeanWrapperFieldExtractor<Transaction>().apply {
                    this.setNames(arrayOf(
                        "reference",
                        "accountNumber",
                        "description",
                        "startBalance",
                        "mutation",
                        "endBalance"
                    ))
                }
            )
        }
    }
}

class TransactionFileHeaderCallbackAgain : FlatFileHeaderCallback {
    override fun writeHeader(writer: Writer) {
        writer.write("Reference,Account Number,Description,Start Balance,Mutation,End Balance")
    }
}