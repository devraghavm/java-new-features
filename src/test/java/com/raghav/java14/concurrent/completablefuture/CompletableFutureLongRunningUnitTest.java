package com.raghav.java14.concurrent.completablefuture;

import checkers.units.quals.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureLongRunningUnitTest {
    @Test
    public void whenRunningCompletableFutureAsynchronously_thenGetMethodWaitsForResult() throws InterruptedException, ExecutionException {
        Future<String> completableFuture = calculateAsync();

        String result = completableFuture.get();
        Assertions.assertEquals("Hello", result);
    }

    private Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool()
                .submit(() -> {
                    Thread.sleep(500);
                    completableFuture.complete("Hello");
                    return null;
                });
        return completableFuture;
    }

    @Test
    public void whenRunningCompletableFutureWithResult_thenGetMethodReturnsImmediately() throws ExecutionException, InterruptedException {
        Future<String> completableFuture = CompletableFuture.completedFuture("Hello");

        String result = completableFuture.get();
        Assertions.assertEquals("Hello", result);
    }

    public Future<String> calculateAsyncWithCancellation() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool()
                .submit(() -> {
                    Thread.sleep(500);
                    completableFuture.cancel(false);
                    return null;
                });
        return completableFuture;
    }

    @Test
    public void whenCancelingTheFuture_thenThrowsCancellationException() {
        Future<String> future = calculateAsyncWithCancellation();
        Assertions.assertThrows(CancellationException.class, () -> {
            future.get();
        });
    }

    @Test
    public void whenCreatingCompletableFutureWithSupplyAsync_thenFutureReturnsValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        Assertions.assertEquals("Hello", future.get());
    }

    @Test
    public void whenAddingThenAcceptToFuture_thenFunctionExecutesAfterComputationIsFinished() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture.thenAccept(s -> System.out.println("Computation Returned: "+ s));

        future.get();
    }

    @Test
    public void whenAddingThenRunToFuture_thenFunctionExecutesAfterComputationIsFinished() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture.thenRun(() -> System.out.println("Computation Finished."));

        future.get();
    }

    @Test
    public void whenAddingThenApplyToFuture_thenFunctionExecutesAfterComputationIsFinished() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");

        Assertions.assertEquals("Hello World", future.get());
    }

    @Test
    public void whenUsingThenCompose_thenFuturesExecuteSequentially() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        Assertions.assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void whenUsingThenCombine_thenWaitForExecutionOfBothFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

        Assertions.assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void whenUsingThenAcceptBoth_thenWaitForExecutionOfBothFutures() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> System.out.println(s1 + s2));
    }

    @Test
    public void whenFutureCombinedWithAllOfCompletes_thenAllFuturesAreDone() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);

        // ...

        combinedFuture.get();

        Assertions.assertTrue(future1.isDone());
        Assertions.assertTrue(future2.isDone());
        Assertions.assertTrue(future3.isDone());

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        Assertions.assertEquals("Hello Beautiful World", combined);
    }

    @Test
    public void whenFutureThrows_thenHandleMethodReceivesException() throws ExecutionException, InterruptedException {
        String name = null;

        // ...
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        })
                .handle((s, t) -> s != null ? s : "Hello, Stranger!");

        Assertions.assertEquals("Hello, Stranger!", completableFuture.get());
    }

    @Test
    public void whenCompletingFutureExceptionally_thenGetMethodThrows() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // ...
        completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));
        // ...

        Assertions.assertThrows(ExecutionException.class, () -> {
            completableFuture.get();
        });
    }

    @Test
    public void whenAddingThenApplyAsyncToFuture_thenFunctionExecutesAfterComputationIsFinished() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture.thenApplyAsync(s -> s + " World");

        Assertions.assertEquals("Hello World", future.get());
    }

    @Test
    public void whenPassingTransformation_thenFunctionExecutionWithThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> finalResult = compute().thenApply(s -> s + 1);

        Assertions.assertEquals(11, finalResult.get());
    }

    @Test
    public void whenPassingPreviousStage_thenFunctionExecutionWithThenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> finalResult = compute().thenCompose(this::computeAnother);

        Assertions.assertEquals(20, finalResult.get());
    }

    private CompletableFuture<Integer> compute() {
        return CompletableFuture.supplyAsync(() -> 10);
    }

    private CompletableFuture<Integer> computeAnother(Integer i) {
        return CompletableFuture.supplyAsync(() -> 10 + i);
    }

}
