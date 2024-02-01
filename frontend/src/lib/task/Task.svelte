<script>
    import Modal from "../common/Modal.svelte";
    import {createEventDispatcher, onMount} from "svelte";

    export let showModal = false;
    export let task;
    export let taskStatuses;
    let newDescription;
    let newExecutionDate;
    let newStatus;
    const dispatch = createEventDispatcher();

    const handleUpdateTaskData = () => {
        dispatch('updateTaskData', {
            newDescription: newDescription,
            newExecutionDate: newExecutionDate,
            newTaskStatus: newStatus
        });
        reset();
    }

    const reset = () => {
        newDescription = undefined;
        newExecutionDate = undefined;
        newStatus = undefined;
    }

    $: if (showModal === false) {
        reset();
    }
</script>

{#if task}
    <Modal bind:showModal>
        <h2 slot="header">
            Edit Task
        </h2>

        <div class="grid grid-cols-1 md:grid-rows-1 md:grid-cols-12 gap-4">
            <div class="md:col-span-4 p-2 border-2 border-orange-400 rounded">
                <p><span class="font-bold">Description:</span> {task.description || 'n/a'}</p>
                <p><span class="font-bold">Execution Date:</span> {task.executionDate || 'n/a'}</p>
                <p><span class="font-bold">Status:</span> {task.taskStatus.toLowerCase()}</p>
            </div>

            <div class="md:col-span-8 flex flex-col p-2 border-2 border-orange-400 rounded">
                <div class="grid grid-cols-1 md:grid-cols-3 md:mt-2">
                    <label class="mt-2">New Description</label>
                    <input type="text" bind:value={newDescription} class="w-full md:col-span-2 px-4 py-2 border rounded focus:outline-none focus:border-blue-500">
                </div>
                <div class="grid grid-cols-1 md:grid-cols-3 mt-4">
                    <label class="mt-2">New Execution Date</label>
                    <input type="date" bind:value={newExecutionDate} min={new Date().toISOString().split('T')[0]} class="md:col-span-2 w-fit px-4 py-2 border rounded focus:outline-none focus:border-blue-500">
                </div>
                <div class="grid grid-cols-1 md:grid-cols-3 mt-4">
                    <label class="mt-2">New Status</label>
                    <select
                            class="md:col-span-2 flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-900 bg-gray-200 border border-gray-300 rounded-l-lg cursor-pointer hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-100"
                            bind:value={newStatus}>
                        <option disabled selected value={undefined}>Select Here</option>
                        {#each taskStatuses as status}
                            <option value={status}>{status.toLowerCase()}</option>
                        {/each}
                    </select>
                </div>
            </div>
        </div>

        <div slot="custom-buttons">
            <button
                class="mt-1 md:mt-0 md:ml-2 px-2 py-1 border-2 border-white rounded text-white md:hover:shadow-md md:hover:shadow-white"
                on:click={handleUpdateTaskData}>Save
            </button>
        </div>
    </Modal>
{/if}